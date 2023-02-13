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
