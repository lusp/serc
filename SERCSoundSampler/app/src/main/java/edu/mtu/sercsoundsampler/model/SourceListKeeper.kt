package edu.mtu.sercsoundsampler.model

class SourceListKeeper(val helper: PreferencesHelper) {
    var adapter: SourceAdapter? = null
    companion object {
        val TAG = "SourceListKeeper"
        val KEY_SOURCE_LIST = "edu.mtu.src.list"
    }
    fun add(source: String) {
        var array: ArrayList<String> = helper.recallList(KEY_SOURCE_LIST)
        if (!contains(source)) {
            array.add(0, source)
            helper.preserveList(KEY_SOURCE_LIST, array)
            adapter?.notifyDataSetChanged()
        }
    }
    fun getCount(): Int {
        return list().size
    }
    fun getItem(i: Int): String? {
        val el = list()
        return if (i >= 0 && i < el.size) el[i] else null
    }
    fun contains(s: String): Boolean {
        var rv = false
        list().map { if (it.equals(s, true)) rv = true }
        return rv
    }
    fun list(): List<String> {
        return helper.recallList(KEY_SOURCE_LIST)
    }
    fun clear() {
        helper.clearAll()
    }
}