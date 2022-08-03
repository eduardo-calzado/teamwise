package es.eduardocalzado.teamwise.domain

data class PlayerInfoItem(
    val title: String = "",
    val value: String = "",
    val isHeader: Boolean = false,
    val isNested: Boolean = false
)