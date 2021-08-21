package br.com.andersonp.droidhelper

/**
 * Collector - The grandmaster of sets
 *
 * @constructor Create empty Collector
 */
object Collector {

    /**
     * Remove blank strings from a String List
     *
     * @return the cleaner List, if there was any empty element
     */
    fun List<String>.removeBlanks(): List<String> {
        return this.filter { it != ""}
    }

    /**
     * Remove blank string and null element in a Nullable String List
     *
     * @return the cleaner List, if there was any empty and/or null element
     */
    fun List<String?>.removeBlanksNulls(): List<String> {
        return this.filterNotNull().removeBlanks()
    }
}