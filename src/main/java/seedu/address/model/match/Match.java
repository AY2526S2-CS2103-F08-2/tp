package seedu.address.model.match;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Match in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Match {

    // identity fields
    private final OpponentName opponentName;
    private final Date matchDate;

    // data fields
    private final MatchPlayerList matchPlayerList;

    /**
     * Every field must be present and not null.
     */
    public Match(OpponentName opponentName, Date matchDate, MatchPlayerList matchPlayerList) {
        requireAllNonNull(opponentName, matchDate, matchPlayerList);
        this.opponentName = opponentName;
        this.matchDate = matchDate;
        this.matchPlayerList = matchPlayerList;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public OpponentName getOpponentName() {
        return opponentName;
    }

    public MatchPlayerList getMatchPlayerList() {
        return matchPlayerList;
    }

    /**
     * Returns true if both matches have the same date and opponent name.
     * This defines a weaker notion of equality between two matches.
     */
    public boolean isSameMatch(Match otherMatch) {
        if (otherMatch == this) {
            return  true;
        }

        return otherMatch != null
                && otherMatch.getMatchDate().equals(getMatchDate())
                && otherMatch.getOpponentName().equals(getOpponentName());
    }

    /**
     * Returns true if both matches have the same identity and data fields.
     * This defines a stronger notion of equality between two matches.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Match)) {
            return false;
        }

        Match otherMatch = (Match) other;
        return matchDate.equals(otherMatch.matchDate)
                && opponentName.equals(otherMatch.opponentName)
                && matchPlayerList.equals(otherMatch.matchPlayerList);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(opponentName, matchDate, matchPlayerList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("opponent name", opponentName)
                .add("match date", matchDate)
                .add("players", matchPlayerList)
                .toString();
    }
}
