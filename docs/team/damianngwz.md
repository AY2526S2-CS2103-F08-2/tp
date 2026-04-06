---
layout: page
title: DamianNgWZ's Project Portfolio Page
---

### Project: AddressBook Level 3

AddressBook - Level 3 is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 15kLoC.

Given below are my contributions to the project.

* **New Feature**: Added role-scoped listing commands (`list r/player`, `list r/staff`).
    * **What it does**: Extends the `list` command so users can filter the visible person list directly by role, while preserving the original `list` command as the all-persons view.
    * **Justification**: Team workflows are frequently role-specific. Coaches regularly switch between player operations and staff operations, so a direct role-scoped list reduces command friction and improves navigation speed.
    * **Highlights**: Implemented end-to-end parser-command-predicate integration (`AddressBookParser` -> `ListRoleCommandParser` -> `ListRoleCommand` -> `PersonHasRolePredicate`) with case-insensitive role handling and strict invalid-role rejection. This role-filtering foundation was later reused/extended in role-scoped filtering enhancements (PR [#62](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/62), PR [#76](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/76), PR [#95](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/95)).
    * **Credits**: Followed the abstraction boundaries and command workflow patterns used in AB3 (`Parser` -> `Command` -> `Model` with predicate-based filtered list updates) to keep feature integration consistent with the existing architecture.

* **New Feature**: Added attributes subsystem (`Team`, `Status`, `Position`) with catalog commands and person assignment.
    * **What it does**: Introduces a full catalog-backed attribute domain where users can add/edit/delete/list approved attribute values and assign those values during person creation/editing with validation against catalog state.
    * **Justification**: Replacing free-text team/status/position inputs with validated catalogs reduces data inconsistency and typo-driven errors. This also provides a stable baseline for integrating future features that depend on clean domain categorization (e.g., sorting, filtering, analytics/reporting, and role/attribute-driven workflows).
    * **Highlights**:
        * Established model/storage foundation for attributes (value objects, unique lists, defaults, and persistence path) as the base integration layer (PR [#93](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/93)).
        * Delivered attribute command families incrementally with targeted tests: team commands (PR [#101](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/101)), status commands (PR [#107](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/107)), and position commands (PR [#116](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/116)); final integration was through PR [#107](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/107), which included the `position` increment from PR [#116](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/116) before merge to `master`.
        * Implemented person-level assignment and UI integration, including non-default-only rendering and assignment validation paths (PR [#125](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/125)).
        * Enforced domain rules such as protected defaults, in-use delete blocking, and rename propagation to assigned persons.
    * **Credits**: Followed AB3 command-building and layering conventions for relevant parts of the implementation so the new commands and model operations integrate cleanly with the existing codebase.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=DamianNgWZ&breakdown=true)

* **Project management**:
    * Served as team lead: managed deadlines, coordinated deliverables, and assigned tasks across teammates.
    * Helped the team set up the repository baseline and collaboration workflow.
    * Managed release preparations across all release iterations. `v1.3`-`v1.5` (3 releases)

* **Enhancements to existing features**:
    * Extended role-scoped filtering behavior through role-aware search/filter enhancements (PR [#95](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/95)).
    * Refined attribute behavior after initial delivery (e.g., canonical casing and storage/default handling) through follow-up attribute integration work (PR [#125](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/125)).

* **Documentation**:
    * User Guide:
        * Updated command documentation and usage clarifications for list/attributes flows (PR [#76](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/76), PR [#127](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/127), PR [#128](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/128)).
    * Developer Guide:
        * Added implementation and interaction details for role-scoped list and attributes architecture/behavior, including use cases and testing notes (PR [#62](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/62), PR [#125](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/125)).
        * Updated use-case and DG-related content during early project phases (PR [#46](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/46)).

* **Community**:
    * PRs reviewed (with non-trivial review comments): PR [#136](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/136), PR [#126](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/126),
      PR [#114](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/114), PR [#112](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/112), PR [#103](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/103), PR [#90](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/90), PR [#81](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/81), PR [#75](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/75), PR [#67](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/67), PR [#51](https://github.com/AY2526S2-CS2103-F08-2/tp/pull/51)
  
* **Extra Templates**: (To be removed if irrelevant)

* **Tools**:
    * Set up and maintained the team repository workflow baseline (branching/merge flow and collaboration setup).
    * Used CI/test workflows and local verification routines to support stable integration before releases.

* _{you can add/remove categories in the list above}_
