# Mobile Tolling System
Description

## Getting Started

### Version Control - Git
As this project is being mirrored into two git repositories, the following configuration is advised.

```
git clone <repo_hyperlink>
git remote add all <repo_hyperlink>
git remote set-url --add --push all <other_repo_hyperlink>
git remote set-url --add --push all <repo_hyperlink>
```
Now you can push for the 2 repositories at once, using:
```
git push all master
```

For checking if all is correctly configured use:
```
git remove -v
//or for more advanced info
git config -l | grep '^remote\.all'
```
