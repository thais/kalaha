package com.game.kalaha.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
@Document
class Player(val id: Long, var name: String)