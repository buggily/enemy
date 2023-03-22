package com.buggily.enemy.core.query

import android.content.ContentResolver
import android.os.Bundle

class Query(
    private val selections: Selections,
    private val sort: Sort,
) {

    var limit: Limit? = null
    var offset: Offset? = null

    val bundle: Bundle
        get() = Bundle().apply {
            putAll(selections.bundle)
            putAll(sort.bundle)

            limit?.let { putAll(it.bundle) }
            offset?.let { putAll(it.bundle) }
        }

    class Selections(
        private vararg val identity: Selection,
    ) {

        val bundle: Bundle
            get() = Bundle().apply {
                val selectionIdentity: String = identity.joinToString(separator = " ") {
                    it.identity
                }

                val expressions: List<Selection.Expression> = identity.filterIsInstance(
                    klass = Selection.Expression::class.java,
                )

                val selectionArgsIdentity: Array<out String> = expressions.map {
                    it.expressionIdentity.toString()
                }.toTypedArray()

                putString(ContentResolver.QUERY_ARG_SQL_SELECTION, selectionIdentity)
                putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, selectionArgsIdentity)
            }

        companion object {
            val NONE: Selections
                get() = Selections()
        }
    }

    sealed class Selection {

        abstract val identity: String

        sealed class Expression(
            open val argumentIdentity: String,
            open val expressionIdentity: Any?,
        ) : Selection() {

            abstract val operator: String

            override val identity: String
                get() = "$argumentIdentity $operator ?"

            class Equals(
                override val argumentIdentity: String,
                override val expressionIdentity: Any?,
            ) : Expression(
                argumentIdentity = argumentIdentity,
                expressionIdentity = expressionIdentity,
            ) {

                override val operator: String
                    get() = "="
            }

            class Unequals(
                override val argumentIdentity: String,
                override val expressionIdentity: Any?,
            ) : Expression(
                argumentIdentity = argumentIdentity,
                expressionIdentity = expressionIdentity,
            ) {

                override val operator: String
                    get() = "!="
            }

            class Like(
                override val argumentIdentity: String,
                override val expressionIdentity: Any?,
            ) : Expression(
                argumentIdentity = argumentIdentity,
                expressionIdentity = expressionIdentity,
            ) {

                override val operator: String
                    get() = "LIKE"
            }
        }

        sealed class Operator : Selection() {

            object And : Operator() {

                override val identity: String
                    get() = "AND"
            }

            object Or : Operator() {

                override val identity: String
                    get() = "OR"
            }
        }
    }

    class Sort(
        private val columns: Map<String, Type>,
        private val direction: Direction?,
    ) {

        sealed class Type {

            abstract val identity: String

            object Text : Type() {

                override val identity: String
                    get() = "VARCHAR"
            }

            object Number : Type() {

                override val identity: String
                    get() = "NUMERIC"
            }
        }

        val bundle: Bundle
            get() = Bundle().apply {
                columnsIdentity?.let { putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, it) }
                directionIdentity?.let { putInt(ContentResolver.QUERY_ARG_SORT_DIRECTION, it) }
            }

        private val columnsIdentity: Array<out String>?
            get() = columns.takeUnless { it.isEmpty() }?.map {
                with(it) { "CAST($key AS ${value.identity})" }
            }?.toTypedArray()

        private val directionIdentity: Int?
            get() = direction?.identity

        sealed class Direction {

            abstract val identity: Int

            object Ascending : Direction() {

                override val identity: Int
                    get() = ContentResolver.QUERY_SORT_DIRECTION_ASCENDING
            }

            object Descending : Direction() {

                override val identity: Int
                    get() = ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
            }
        }

        companion object {
            val NONE: Sort
                get() = Sort(
                    columns = emptyMap(),
                    direction = null,
                )
        }
    }

    class Limit(
        private val identity: Int,
    ) {

        val bundle: Bundle
            get() = Bundle().apply { putInt(ContentResolver.QUERY_ARG_LIMIT, identity) }
    }

    class Offset(
        private val identity: Int,
    ) {

        val bundle: Bundle
            get() = Bundle().apply { putInt(ContentResolver.QUERY_ARG_OFFSET, identity) }
    }

    companion object {
        val NONE: Query
            get() = Query(
                selections = Selections.NONE,
                sort = Sort.NONE,
            )
    }
}
