# MtxUtil
A collection of data sctructures and other utilities

## Lists
`MtxList` is a general purpose list. It has four implementations:
* `MtxLinkedList` - `O(n)` lookup and `O(1)` insert
* `MtxArrayList` - `O(1)` lookup and `O(1)` insert, but periodically expensive adds
* `MtxHashList` - `O(1)` lookup and `O(1)` insert, but periodically expensive removes
* `MtxStringList` - `O(n)` lookup and `O(n)` insert, but `O(1)` `toString()`s
