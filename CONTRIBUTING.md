# Contribution Guidelines

## Branch Names
Branches should be named after either their developer or purpose. Branch names should be camel case and not contain any spaces, underscores (`_`), or dashes (`-`, except for in codespace names).

### Single purpose branches
These branches are created for one change, followed by one pull request, and are typically deleted after merging.

Naming convention: 
  * `feature/<description>` - code to add new functionality
  * `bug/<description>` - code to fix / improve existing functionality
  * `change/<description>` - changes to things like documentation and meta / constants
    * or for anything else really

Examples:
  * `feature/addNewListMethods`
  * `bug/fixHashListUnitTests`
  * `change/addContributingFile`
 
 ### Developer owned branches
 These branches are used by individual developers or codespaces and sometimes have higher perms.
 
 Naming convention:
  * `user/<github username>`
  * `user/codespace/<codespace name>`

## Coding Conventions
Use common sense when writing and arranging your code. Listed here are some generally expected practices for this repo, but if no particular rule is specified here just follow normal Java standards.

### Class contents ordering
  1. Javadoc
  2. `class` or `interface` statement
  3. `static` variables
  4. `final` instance variables
  5. mutable instance variables
  6. constructors
  7. overriding methods
  8. `public` methods
  9. general purpose `private` methods

Notes:
  * Classes should not contain any `static` methods unless the class is a utility class, marked by having a private constructor.
  * `private` methods that are directly used by one or more `public` methods for a single purpose may go under that method instead of at the bottom section.
  * `public` methods that extend on the functionality of an overridden method (by overloading it, for example) may go under that method instead of in the public method section.

### Class naming
  * All class names should start with `Mtx`
  * All subclass names should be written as: `Mtx` + `<name>` + `<super class name>`
    * **Example:** `MtxArrayList` and `MtxStringList` inherit `MtxList`
  * All utility class names should end with `Util`
    * **Example:** `MtxTimeUtil`

### Unit testing
Please add unit tests for all new functionality! This is a utility library so most code will not be user-tested unless it has unit tests. The goal is to maintain 100% code coverage.

  * Unit test classes should be named: `<class name>` + `Test`
    * **Example:** `MtxTimeUtilTest` contains the tests for `MtxTimeUtil`
  * Unit test methods should be named: `test` + `<method name>` (capitalized) + `_<testcase>` (optional)
    * So basically just name all unit tests the same as the method being tested, prefixed by the word `test`, in camel case.
    * If the method being tested needs multiple unit tests, name them all the same + the test case description, separated by an underscore (`_`)
    * **Example:** `testAddAll_argContainingDelimiter()` and `testAddAll_addToNonEmptyList()` will test the method `addAll()`