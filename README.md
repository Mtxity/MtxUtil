# MtxUtil
A collection of data sctructures and other utilities

## Lists
`MtxList` is a general purpose list. It has four implementations:
* `MtxLinkedList` - `O(n)` lookup and `O(1)` insert
* `MtxArrayList` - `O(1)` lookup and `O(1)` insert, but periodically expensive adds
* `MtxHashList` - `O(1)` lookup and `O(1)` insert, but periodically expensive removes
* `MtxStringList` - `O(n)` lookup and `O(n)` insert, but `O(1)` `toString()`s

All of these support `equals()` and `iterator()` as well as the basic functions. (`size()`, `toArray()`, `sort()`, etc.) See the [interface](https://github.com/Mtxity/MtxUtil/blob/main/src/main/java/com/edavalos/mtx/util/list/MtxList.java) for a complete list.

## Stacks and Queues
These are simplified versions of their equivalent Java standard library implementations.
* `MtxStack` - supports `push()`, `pop()` and `peek()`
* `MtxQueue` - supports `enqueue()` and `dequeue()`

Both of these support `size()` and `toString()`

## Sets
`MtxSet` is a general purpose set. It has three implementations:
* `MtxHashSet` - unordered list of unique elements
* `MtxLinkedSet` - ordered list of unique elements
* `MtxSortedLinkedSet` - ordered and sorted list of unique elements

All of these support `equals()` and `iterator()` as well as the basic functions. (`contains()`, `toArray()`, `toString()`, `containsAll()`, etc.) See the [interface](https://github.com/Mtxity/MtxUtil/blob/main/src/main/java/com/edavalos/mtx/util/set/MtxSet.java) for a complete list.

## Time
`MtxTime` is an object for keeping a record of a time vector, with operations to modify it accordingly. It can be instantiated with a string to parse into a time, or directly with integers for each component. It will also self balance (so that its hours are no greater than 23 and its seconds and minutes are no greater than 59).

It has support for these methods:
* `addTime()` - adds a time to this time
* `subtractTime()` - subtracts a time from this time
* `multiplyBy()` - multiplies this time by an integer
* `isLongerTHan()` - checks if this time vector is longer than another one

## Hashing
`MtxHash` will securely encrypt sensitive strings. All implementations hash the values a couple of thousand times and add a salt at every iteration.

These are its implementations:
<!-- @TODO: Add links to websites explaining the different MD hashing algorithms -->
* `MtxSha256Hash` - hashes strings with the SHA-256 algorithm. Hashes value 5,000 times.
* `MtxSha3Hash` - hashes strings with the SHA3-256 algorithm. Hashes value 2,000 times.
* `MtxMD5Hash` - hashes strings with the MD5 algorithm. Hashes value 9,000 times.
* `MtxHashmapHash` - hashes strings with the algorithm that Java hashmaps use. Hashes value 20,000 times.

## Encryption

## String Filters

## Matrix Formatting

## String Utilities

## HTTP Request Reader

## Graphs

## Elevator

## File Parsers
These will take care of boilerplate file scanning as well as content parsing.

Currently these are supported:
* `MtxFileReader` - basic scanning of `txt` files. Will take in a filepath and provide a string array of rows.
* `MtxCsvParser` - Will take in a filepath to a `csv` file and provide a 2D string array representing the matrix in the file.