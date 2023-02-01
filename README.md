# MtxUtil
A collection of data sctructures and other utilities

## Lists
`MtxList` is a general purpose list. It has two implementations:
* `MtxLinkedList` - `O(n)` lookup but `O(1)` insert
* `MtxArrayList` - `O(1)` lookup but periodically expensive insert (every `10 * 1.8n` inserts)
