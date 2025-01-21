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

                val expressions: List<Selection.Expression> =
                    identity.filterIsInstance<Selection.Expression>()

                val selectionArgsIdentity: Array<out String> = expressions.map {
                    it.expressionIdentity.toString()
                }.toTypedArray()

                putString(ContentResolver.QUERY_ARG_SQL_SELECTION, selectionIdentity)
                putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, selectionArgsIdentity)
            }

        companion object {
            val NONE = Selections()
        }
    }

    sealed interface Selection {

        val identity: String

        sealed interface Expression : Selection {

            val operator: String
            val argumentIdentity: String
            val expressionIdentity: Any?

            override val identity: String
                get() = "$argumentIdentity $operator ?"

            class Equals(
                override val argumentIdentity: String,
                override val expressionIdentity: Any?,
            ) : Expression {

                override val operator: String = "="
            }

            class Unequals(
                override val argumentIdentity: String,
                override val expressionIdentity: Any?,
            ) : Expression {

                override val operator: String = "!="
            }

            class Like(
                override val argumentIdentity: String,
                override val expressionIdentity: Any?,
            ) : Expression {

                override val operator: String = "LIKE"
            }
        }

        sealed interface Operator : Selection {

            data object And : Operator {
                override val identity: String = "AND"
            }

            data object Or : Operator {
                override val identity: String = "OR"
            }
        }
    }

    data class Sort(
        private val columns: Map<String, Type>,
        private val direction: Direction?,
    ) {

        sealed interface Type {

            val identity: String

            data object Text : Type {
                override val identity: String = "VARCHAR"
            }

            data object Number : Type {
                override val identity: String = "NUMERIC"
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

        sealed interface Direction {

            val identity: Int

            data object Ascending : Direction {
                override val identity: Int = ContentResolver.QUERY_SORT_DIRECTION_ASCENDING
            }

            data object Descending : Direction {
                override val identity: Int = ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
            }
        }

        companion object {
            val NONE = Sort(
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
        val NONE = Query(
            selections = Selections.NONE,
            sort = Sort.NONE,
        )
    }
}
