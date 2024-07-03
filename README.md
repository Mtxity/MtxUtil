# MtxUtil
A collection of data structures and other utilities

# Contents
In order of package names
<!-- @TODO: Add table of contents here -->

## Address ([com.edavalos.mtx.util.address](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/address))
`MtxAddress` stores and parses US and CAN address data

## DB ID Generator ([com.edavalos.mtx.util.db.id](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/db/id))
`MtxAutoIdBuilder` generates IDs given a specified criteria

These are its implementations:
* `MtxChecksumIdGenerator` - uses the Luhn algorithm to get a unique ID.
* `MtxChecksumIterativeIdGenerator` - an ordered version of `MtxChecksumIdGenerator`.
* `MtxIdGenerator` - base class for generating IDs.
* `MtxUuidGenerator` - uses passed data for hashing

## DB Query Generator ([com.edavalos.mtx.util.db.query](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/db/query))
<!-- @TODO: Add description -->

## DB Table Data Holder ([com.edavalos.mtx.util.db.table](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/db/table))
<!-- @TODO: Add description -->

## Specialized Field Wrappers ([com.edavalos.mtx.util.db.var](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/db/var))
<!-- @TODO: Add description -->

## Data Generation ([com.edavalos.mtx.util.generator.data](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/generator/data))
<!-- @TODO: Add description -->

## Random Data Generation ([com.edavalos.mtx.util.generator.random](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/generator/random))
`MtxRandom` generates a random value of most types of primitives.

These are its implementations:
* `MtxIpRandom` - uses the IPv4 address of the machine this is running on as a seed.
* `MtxMemRandom` - uses the number of free RAM bytes of the machine this is running on as a seed.
* `MtxTimeRandom` - uses the current time (in nanoseconds) as a seed.

## Graphs ([com.edavalos.mtx.util.graph](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/graph))
<!-- @TODO: Add description -->

## Graphs ([com.edavalos.mtx.util.grouping](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/grouping))
<!-- @TODO: Add description -->

## Pairs ([com.edavalos.mtx.util.grouping.pair](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/grouping/pair))
<!-- @TODO: Add description -->

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

## Maps ([com.edavalos.mtx.util.map](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/map))
<!-- @TODO: Add description -->

## Math Utils ([com.edavalos.mtx.util.math](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/math))
<!-- @TODO: Add description -->

## Roman Numerals ([com.edavalos.mtx.util.math](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/math))
`MtxRomanNumeral` converts year integers into roman numerals and roman numeral strings into year integers.

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

## String Handlers and Manipulators ([com.edavalos.mtx.util.string](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/string))
<!-- @TODO: Add description -->

## String Utilities ([com.edavalos.mtx.util.string](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/string))
<!-- @TODO: Add description -->

## Time ([com.edavalos.mtx.util.time](https://github.com/Mtxity/MtxUtil/tree/main/src/main/java/com/edavalos/mtx/util/time))
`MtxTime` is an object for keeping a record of a time vector, with operations to modify it accordingly. It can be instantiated with a string to parse into a time, or directly with integers for each component. It will also self balance (so that its hours are no greater than 23 and its seconds and minutes are no greater than 59).

It has support for these methods:
* `addTime()` - adds a time to this time
* `subtractTime()` - subtracts a time from this time
* `multiplyBy()` - multiplies this time by an integer
* `isLongerTHan()` - checks if this time vector is longer than another one

`MtxClock` records the time elapsed (in milliseconds) between when `start()` and `getElapsed()` is called

`MtxTimeUtil` provides several utilities for dealing with times:
* `isValidTime()` - checks if a string fits the pattern used by `MtxTime`
* `extractTimeDigits()` - converts a time string into an integer array of time components
* `getRelativeTimeToNow()` - gets the difference between a specified time and now, in a human-readable format