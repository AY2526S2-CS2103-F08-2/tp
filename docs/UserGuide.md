---
layout: page
title: User Guide
---

SoCcer Manager is a **desktop app for managing players and staff, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, SoCcer Manager can get your team management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your SoCcer Manager data.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all persons.

   * `list players` : Lists only players.

   * `list staff` : Lists only staff.

   * `sort by/name` : Sorts all persons by name in ascending order.

   * `sort players by/email desc` : Sorts only players by email in descending order.

   * `add n/John Doe r/player p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a player named `John Doe` to SoCcer Manager.

   * `delete 3` : Selects the 3rd contact for deletion, then confirm with `y` or `n`.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a player/staff to SoCcer Manager.

Format: `add n/NAME r/ROLE p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

❗The role of the contact **must be specified** (`r/player` or `r/staff`).

Examples:
* `add n/John Doe r/player p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe r/staff t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Adding a match: `match`

Adds a match to the address book.

Format: `match n/OPPONENT_NAME d/DATE [pl/PLAYER_NAME]…​`

Notes: 
- Date must have format `yyyy-MM-dd HHmm`
- Variable number of players can be added to the match, and must exist in the address book

Examples:
- `match n/Mancherster United d/2026-05-15 1600`
- `match n/Mancherster United d/2026-05-15 1600 pl/John Doe`

### Listing persons: `list`

Shows persons in SoCcer Manager, optionally filtered by role.

Format:
* `list` (shows all persons)
* `list players` (shows only players)
* `list staff` (shows only staff)

Notes:
* Role arguments are case-insensitive. e.g. `list PLAYERS`, `list Staff`.
* Invalid role arguments are rejected. Use only `players` or `staff`.

Examples:
* `list`
* `list players`
* `list staff`

### Sorting persons: `sort`

Sorts persons in the UI by a supported attribute.

Format:
* `sort by/ATTRIBUTE`
* `sort players by/ATTRIBUTE`
* `sort staff by/ATTRIBUTE`
* Add optional `desc` at the end for descending order

Supported attributes:
* `name`
* `email`

Examples:
* `sort by/name`
* `sort players by/email`
* `sort staff by/name desc`

### Attributes

SoCcer Manager starts with sample team, status, and position catalog entries in a fresh setup.

#### Listing teams: `teamlist`

Shows all teams in the team catalog.

Format: `teamlist`

Examples:
* `teamlist`

#### Adding a team: `teamadd`

Adds a team to the team catalog.

Format: `teamadd TEAM_NAME`

Examples:
* `teamadd Reserve Team`

#### Editing a team: `teamedit`

Renames an existing team in the team catalog.

Format: `teamedit old/OLD_TEAM_NAME new/NEW_TEAM_NAME`

Examples:
* `teamedit old/First Team new/Reserve Team`

#### Deleting a team: `teamdelete`

Deletes an existing team from the team catalog.

Format: `teamdelete TEAM_NAME`

Examples:
* `teamdelete Reserve Team`

#### Listing statuses: `statuslist`

Shows all statuses in the status catalog.

Format: `statuslist`

Examples:
* `statuslist`

#### Adding a status: `statusadd`

Adds a status to the status catalog.

Format: `statusadd STATUS_NAME`

Examples:
* `statusadd Rehab`

#### Editing a status: `statusedit`

Renames an existing status in the status catalog.

Format: `statusedit old/OLD_STATUS_NAME new/NEW_STATUS_NAME`

Examples:
* `statusedit old/Active new/Rehab`

#### Deleting a status: `statusdelete`

Deletes an existing status from the status catalog.

Format: `statusdelete STATUS_NAME`

Examples:
* `statusdelete Rehab`

#### Listing positions: `positionlist`

Shows all positions in the position catalog.

Format: `positionlist`

Examples:
* `positionlist`

#### Adding a position: `positionadd`

Adds a position to the position catalog.

Format: `positionadd POSITION_NAME`

Examples:
* `positionadd Winger`

#### Editing a position: `positionedit`

Renames an existing position in the position catalog.

Format: `positionedit old/OLD_POSITION_NAME new/NEW_POSITION_NAME`

Examples:
* `positionedit old/Defender new/Center Back`

#### Deleting a position: `positiondelete`

Deletes an existing position from the position catalog.

Format: `positiondelete POSITION_NAME`

Examples:
* `positiondelete Winger`

### Editing a person : `edit`

Edits an existing person in SoCcer Manager.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/ROLE] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords, optionally limited by role.

Format: `find [r/ROLE] KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Prefixing with `r/player` or `r/staff` limits the results to that role.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find r/player John` returns players whose names match `John`
* `find r/staff alex david` returns staff whose names match `alex` OR `david`
* `find staff ben` treats `staff` as a normal name keyword (general search)
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes a person from SoCcer Manager by list index or name keywords.

Format: `delete INDEX` or `delete KEYWORD [MORE_KEYWORDS]`

* `delete INDEX` selects the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* `delete KEYWORD [MORE_KEYWORDS]` searches by name (same name-matching rules as `find`).
* If one person matches, it will show that person and ask for confirmation.
* If multiple persons match, it will show a clash list with indexes. Enter the clash index to choose a person.
* To confirm or cancel deletion, type `y`/`Y` or `n`/`N`.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2`, then `y` deletes the 2nd person in SoCcer Manager.
* `delete Bernice`, then `n` cancels deletion.
* `delete Meier`, then `2`, then `y` deletes the 2nd matched person in the clash list.

### Bulk deleting persons by tag : `deletebulk`

Deletes all persons that share a specified tag.

Format: `deletebulk t/TAG`

* `deletebulk t/TAG` filters and shows matching persons in the GUI list and CLI message.
* To confirm or cancel bulk deletion, type `y`/`Y` or `n`/`N`.
* Both players and staff with the specified tag are considered.

Examples:
* `deletebulk t/graduated`, then `y` deletes all persons tagged `graduated`.
* `deletebulk t/graduated`, then `n` cancels the bulk deletion.

### Clearing all entries : `clear`

Clears all entries from SoCcer Manager.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

SoCcer Manager data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

SoCcer Manager data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, SoCcer Manager will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause SoCcer Manager to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data from your previous SoCcer Manager home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME r/ROLE p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho r/staff p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Match** | `match n/OPPONENT_NAME d/DATE [pl/PLAYER_NAME]…​` <br> e.g., `match n/Mancherster United d/2026-05-15 pl/John Doe`
**Clear** | `clear`
**Delete** | `delete INDEX` or `delete KEYWORD [MORE_KEYWORDS]`<br> e.g., `delete 3` (then `y`), `delete Bernice`, `delete Meier` (then `2`, then `y`)
**Delete Bulk** | `deletebulk t/TAG`<br> e.g., `deletebulk t/graduated` (then `y` or `n`)
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/ROLE] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find [r/ROLE] KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`, `find r/player James`, `find r/staff Alex`
**List** | `list` / `list players` / `list staff`<br> e.g., `list players`
**Sort** | `sort by/ATTRIBUTE [desc]` / `sort players by/ATTRIBUTE [desc]` / `sort staff by/ATTRIBUTE [desc]`<br> e.g., `sort by/name desc`
**Attributes** | team, status, and position catalog commands
**Team** | `teamlist` / `teamadd TEAM_NAME` / `teamedit old/OLD_TEAM_NAME new/NEW_TEAM_NAME` / `teamdelete TEAM_NAME`<br> e.g., `teamadd Reserve Team`, `teamedit old/First Team new/Reserve Team`
**Status** | `statuslist` / `statusadd STATUS_NAME` / `statusedit old/OLD_STATUS_NAME new/NEW_STATUS_NAME` / `statusdelete STATUS_NAME`<br> e.g., `statusadd Rehab`, `statusedit old/Active new/Rehab`
**Position** | `positionlist` / `positionadd POSITION_NAME` / `positionedit old/OLD_POSITION_NAME new/NEW_POSITION_NAME` / `positiondelete POSITION_NAME`<br> e.g., `positionadd Winger`, `positionedit old/Defender new/Center Back`
**Help** | `help`
