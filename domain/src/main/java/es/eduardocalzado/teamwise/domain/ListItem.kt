package es.eduardocalzado.teamwise.domain

data class ListItem(
    val title: String = "",
    val value: String = "",
    val isHeader: Boolean = false,
    val isNested: Boolean = false
) {
    fun getId(): String {
        return title+value+isHeader.toString()+isNested.toString()
    }
}