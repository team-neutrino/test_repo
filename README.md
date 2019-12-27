# test_repo
## Conventions

### Variables
- Constants
    - Preceded by `k`
    - First word should be the name of the subsystem the constant is for
    - Ex: `kDriveLeftRearTalonId`, `kLimelightFov`, `kControlLoopPeriod`
- Fields
    - Preceded by `m`
    - Fields are any variables found in the **class body**
    - Ex: `mWristAngle`, `mGyroAngle`
- Parameters
    - Preceded by `p`
    - Ex:
    ```
        public DriveStraight(Drive pDrive, double pDistanceToDrive)
        {
            mDrive = pDrive;
            mDistanceToDrive = pDistanceToDrive;
        }
    ```
### Enumerations
- Enumeration classes are in
- Enumerations are in all caps, `LIKE_THIS`
- Ex: States of a manipulator:
```
public enum EManipulatorState
{
    RESET, GRABBING, PLACING;
}
```
## Contributing

Here's how to get your code into the main robot repository:

### If you've just joined the team:
1. Make an account on [GitHub](https://github.com/).
2. Ask one of the robot programming leads to add your account to the Team Neutrino organization on GitHub.

### If it's the first time you've contributed to this repo:
1. Clone the repo to your computer

### Any time you want to make a change:
We use a feature branch workflow. You can read more about that [here](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow).

1. Create and checkout a new branch.
  * `git checkout -b <your_branch_name>`, where <your_branch_name> is a descriptive name for your branch. For example `fix-shooter-wheel`, `two-ball-auto`, or `climbing`. Use dashes in the branch name, not underscores.
2. Make whatever code changes you want/need/ to make. Be sure to write tests for your changes!
3. Commit your work locally.
  * Try to make your commits as atomic (small) as possible. For example, moving functions around should be different from adding features, and changes to one subsystem should be in a different commit than changes to another subsystem.
  * Follow [these](http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html) conventions for commit messages. Or else.
  * If your change is anything more than a few lines or small fixes, don't skip the extended description. If you are always using `git commit` with the `-m` option, stop doing that.
4. Push to your branch.
  * `git push origin <your_branch_name>`.
5. Submit a pull request.
  1. Log into Github.
  2. Go to the page for your forked repo.
  3. Select the branch that you just pushed from the "Branch" dropdown menu.
  4. Click "New Pull Request".
  5. Review the changes that you made.
  6. If you are happy with your changes, click "Create Pull Request".
6. Wait
  * People must review (and approve of) your changes before they are merged - master is locked to any pull requests that don't have at least 1 review.
  * If there are any concerns about your pull request, fix them. Depending on how severe the concerns are, the pull request may be merged without it, but everyone will be happier if you fix your code. 
To update your PR, just push to the branch you made before.
  * Don't dismiss someone's review when you make changes - instead, ask them to re-review it.
7. Merge your changes into master
  * If there are no conflicts, push the "Squash and merge" button, write a good commit message, and merge the changes.
  * If there are conflicts, fix them locally on your branch, push them, and then squash and merge.
8. ???
9. Profit

Special thanks for Team 1885 for posting their documentation online. 
