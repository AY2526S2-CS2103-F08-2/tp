---
layout: page
title: Rui Sheng's Project Portfolio Page
---

### Project: SoCcer Manager

AddressBook - Level 3 is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to create new training sessions.
  * What it does: allows the user to add new training sessions to the address book, directly supplying a list of players or adding all players with a specific attribute.
  * Justification: This feature improves the product significantly because it aids the user in managing training sessions in an app designed for a soccer academy. It is expected that there will be many training sessions and this helps to manage and track them.
  * Highlights: This feature makes use of the Event superclass so that similar code between matches and training can be abstracted, preventing repeated code. This also allows shared behaviour.
  * Credits: Made use of AB3 existing parser and command design to implement this feature.

* **New Feature**: Added the ability to mark attendance for events.
  * What it does: allows the user to mark the attendance of specific players for the specified event.
  * Justification: This feature aids the user in managing players. Users can easily update the attendance of players for the events. Users can also see who has not showed up for a specific event.
  * Highlights: This feature uses (P) to represent a player is present and (NP) to represent not present.
  
* **New Feature**: Added an attendance command to display attendance rates of all players.
  * What it does: allows the user to generate an attendance report quickly.
  * Justification: This feature significantly improves the quality of life of the app. User can use this command to quickly see players who are not showing up for events that they were expected to show up for.
  * Highlights: This feature displays attendance rates in descending order to one decimal place, allowing the user to quickly spot players who are consistently showing up and also those who are always missing from events.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=Arrus218&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=)

* **Project management**:
  * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Refactored large parts of the previous codebase to match new project specifications (Pull requests [\#79](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/79/), [\#64](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/64/))
  * Wrote tests for existing features to increase coverage (Pull requests [\#169](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/169), [\#79](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/79))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `trainingadd`, `attendance` and `attendancemark` [\#177](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/177), [\#132](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/132)
  * Developer Guide:
    * Added implementation details of the role class and added UML diagram for model component [\#139](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/139).

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#105](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/105), [\#118](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/118)
  * Contributed to forum discussions (examples: [1](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/43))
  * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
  
