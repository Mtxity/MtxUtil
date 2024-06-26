# MtxUtil
A collection of data structures and other utilities

# Contents
In order of package names

## Hashing ([com.edavalos.mtx.util.hash](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/hash))
`MtxHash` will securely encrypt sensitive strings. All implementations hash the values a couple of thousand times and add a salt at every iteration.

These are its implementations:
<!-- @TODO: Add links to websites explaining the different MD hashing algorithms -->
* `MtxSha256Hash` - hashes strings with the SHA-256 algorithm. Hashes value 5,000 times.
* `MtxSha3Hash` - hashes strings with the SHA3-256 algorithm. Hashes value 2,000 times.
* `MtxMD5Hash` - hashes strings with the MD5 algorithm. Hashes value 9,000 times.
* `MtxHashmapHash` - hashes strings with the algorithm that Java hashmaps use. Hashes value 20,000 times.

## Encryption ([com.edavalos.mtx.util.hash.encrypt](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/hash/encrypt))
`MtxEncryptor` and `MtxInsecureEncryptor` will convert strings to gibberish and then convert them back to their original values.
* `MtxEncryptor` - uses [Javax.Crypto](https://docs.oracle.com/javase/8/docs/api/javax/crypto/package-summary.html) to convert strings into hashes. Slower but virtually unresolvable.
* `MtxInsecureEncryptor` - uses an encryption matrix to generate hashes. Fast, and secure for most use cases.

## File Parsers ([com.edavalos.mtx.util.io](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/io))
These will take care of boilerplate file scanning as well as content parsing.

Currently, these are supported:
<!-- @TODO: Decide what to do about MtxXmlParser -->
* `MtxFileReader` - basic scanning of `txt` files. Will take in a filepath and provide a string array of rows.
* `MtxCsvParser` - Will take in a filepath to a `csv` file and provide a 2D string array representing the matrix in the file.
* `MtxJsonParser` - Will take in a filepath to a `json` file and provide a `LinkedHashMap<String, Object>` representing the json in the file.

## Lists ([com.edavalos.mtx.util.list](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/list))
`MtxList` is a general purpose list. It has four implementations:
* `MtxLinkedList` - `O(n)` lookup and `O(1)` insert
* `MtxArrayList` - `O(1)` lookup and `O(1)` insert, but periodically expensive adds
* `MtxHashList` - `O(1)` lookup and `O(1)` insert, but periodically expensive removes
* `MtxStringList` - `O(n)` lookup and `O(n)` insert, but `O(1)` `toString()`s
* `MtxIntrusiveLinkedList` - `O(n)` lookup and `O(1)` insert, but marginally faster than `MtxLinkedList`

All of these support `equals()` and `iterator()` as well as the basic functions. (`size()`, `toArray()`, `sort()`, etc.) See the [interface](https://github.com/Mtxity/MtxUtil/blob/main/src/main/java/com/edavalos/mtx/util/list/MtxList.java) for a complete list.

## Stacks and Queues ([com.edavalos.mtx.util.list.line](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/list/line))
These are simplified versions of their equivalent Java standard library implementations.
* `MtxStack` - supports `push()`, `pop()` and `peek()`
* `MtxQueue` - supports `enqueue()` and `dequeue()`

Both of these support `size()` and `toString()`

This is a representation (and real-world use case) of a multi-priority and bidirectional stack:
* `MtxElevator` - controlled with `queueFloor()` and `moveToNextFloor()`

## HTTP Request Reader ([com.edavalos.mtx.util.network](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/network))
`MtxRequestReader` takes a serialized HTTP request and parses it.
Something like: `GET /test/14/twelve?key1=value1&key2=value2 HTTP/1.1` would be consumed by an `MtxRequestReader` object to have the following properties accessible:
* `requestMethod`: `GET`
* `httpVersion`: `HTTP/1.1`
* `fixedParams`: `["test", "14", "twelve"]`
* `anchor`: `null`
* `queryParams`: `key1=value1`, `key2=value2`
* `hasQueryParams`: `true`
* `hasAnchor`: `false`

## Sets ([com.edavalos.mtx.util.set](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/set))
`MtxSet` is a general purpose set. It has six implementations:
* `MtxHashSet` - unordered list of unique elements backed by a hash map key set
* `MtxLinkedSet` - ordered list of unique elements backed by a linked list
* `MtxSortedLinkedSet` - ordered and sorted list of unique elements backed by a linked list
* `MtxMapSet` - unordered list of unique elements backed by a tree map
* `MtxArraySet` - unordered list of unique elements backed by a dynamic array
* `MtxSortedArraySet` - ordered and sorted list of unique elements backed by a dynamic array

All of these support `equals()` and `iterator()` as well as the basic functions. (`contains()`, `toArray()`, `toString()`, `containsAll()`, etc.) See the [interface](https://github.com/Mtxity/MtxUtil/blob/main/src/main/java/com/edavalos/mtx/util/set/MtxSet.java) for a complete list.

## String Handlers and Manipulators

## String Utilities

## Time
`MtxTime` is an object for keeping a record of a time vector, with operations to modify it accordingly. It can be instantiated with a string to parse into a time, or directly with integers for each component. It will also self balance (so that its hours are no greater than 23 and its seconds and minutes are no greater than 59).

It has support for these methods:
* `addTime()` - adds a time to this time
* `subtractTime()` - subtracts a time from this time
* `multiplyBy()` - multiplies this time by an integer
* `isLongerTHan()` - checks if this time vector is longer than another one