---
layout: page
title: Neo Ryan's Project Portfolio Page
---

### Project: SoCCer Manager

SoCCer Manager is a desktop application for managing soccer players and staff. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.

Given below are my contributions to the project.

* **New Feature**: Added safer and more scalable deletion workflows.
    * **What it does**: Improves single-person deletion with explicit confirmation, makes name-based deletion more intuitive, and adds bulk deletion support for multiple categories.
    * **Justification**: Deletion is a high-impact operation. Requiring confirmation and supporting clearer delete flows reduces accidental data loss while still keeping the CLI efficient for frequent maintenance tasks.
    * **Highlights**:
        * Added an explicit confirmation flow for `delete`, including follow-up `y` / `n` handling and supporting interaction logic (PR [#71](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/71)).
        * Refined the `delete` command to be more user-friendly and intuitive for end users (PR [#81](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/81)).
        * Implemented `deletebulk` for mass deletion by tag, then extended it to support `team` and `status` as structured deletion criteria (PR [#103](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/103), PR [#160](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/160)).
        * Designed the bulk-deletion implementation around reusable parsing and confirmation-flow components so the feature could be extended without duplicating command logic.
    * **Credits**: Followed the AB3 parser-command-model interaction pattern and reused the project’s existing filtered-list and command-handling abstractions to integrate the delete workflows cleanly.

* **New Feature**: Added advanced list, sort, and filter support for roster management.
    * **What it does**: Extends the command set so users can view and organize persons by role, team, status, position, and player stats such as goals, wins, and losses.
    * **Justification**: Team-management workflows depend heavily on being able to quickly narrow and reorder the visible roster. These commands make the app more practical for day-to-day operational use.
    * **Highlights**:
        * Extended `list` to support structured filtering by `r/`, `tm/`, `st/`, and `pos/`, including combined filters in one command (PR [#161](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/161)).
        * Added new sort keys for `team`, `status`, `position`, `goals`, `wins`, and `losses`, with preserved `desc` behavior and role-scoped sorting support (PR [#114](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/114), PR [#163](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/163)).
        * Added a dedicated `filter` command for structured attribute filtering and stat-threshold filtering, allowing combinations such as `filter r/player pos/Forward goals/>10` (PR [#172](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/172)).
        * Reused predicate-based filtered-list updates across these features so the list/sort/filter flows remain consistent with the project’s existing architecture.
    * **Credits**: Built on the project’s existing predicate-driven filtered-list model and command parsing style to keep the new viewing and sorting workflows coherent.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=ryrybn&breakdown=true)

* **Project management**:
    * Managed release preparation for `v1.1`.
    * Helped drive feature integration across multiple iterations by splitting larger enhancements into smaller PRs that could be reviewed and merged incrementally.

* **Enhancements to existing features**:
    * Expanded the original sort functionality from basic attributes to roster attributes and stat-based sorting, including syntax refinements for consistency with the rest of the command set (PR [#114](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/114), PR [#163](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/163)).
    * Extended bulk deletion beyond tag-based behavior to cover more realistic roster-maintenance use cases such as deleting by `team` and `status` (PR [#160](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/160)).
    * Improved parser, integration, and coverage quality for newly introduced command flows, especially around list/filter/sort combinations and confirmation-heavy delete behavior.

* **Documentation**:
    * User Guide:
        * Updated command documentation and examples for delete, bulk delete, list, sort, and filter workflows.
        * Documented the expanded sort keys and structured filtering syntax so end-user command formats match the implemented behavior.
    * Developer Guide:
        * Added and refined implementation details for deletion workflows and sorting behavior.
        * Updated command sequence diagrams and feature descriptions to reflect the current parser flow and role-prefix syntax (PR [#149](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/149)).
    * Project website / repo docs:
        * Updated project acknowledgements and team-page content in the early project setup phase (PR [#41](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/41), PR [#34](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/34), PR [#26](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/26), PR [#22](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/22), PR [#14](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/14)).

* **Community**:
    * Contributed substantial feature work through a series of focused PRs covering deletion, sorting, filtering, and documentation updates.
    * Worked iteratively with teammate feedback to improve command consistency, documentation accuracy, and test coverage before integration.

* **Tools**:
    * Used Gradle verification, CI feedback, and coverage reports to harden feature branches before merge.
    * Maintained implementation-aligned UML and documentation artifacts alongside code changes to reduce drift between code and docs.
