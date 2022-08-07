package com.buggily.enemy.domain.map

interface BidirectionalMapper<Input, Output> : FlexibleBidirectionalMapper<Input, Output, Input, Output>
