package seedu.address.logic.importcsv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Deals with CSV importing, managing the parsing and maintaining strict expectations of provided CSV file.
 * See expected format in {@code EXPECTED_HEADERS} constant, and adhere to specific field(s) format requirements.
 */
public class CsvImportService {

    private static final List<String> EXPECTED_HEADERS =
            List.of("name", "role", "address", "phone", "email", "tags"); // forced order

    /**
     * Imports the CSV file from the given path. Expects a properly formatted CSV file.
     * <p/>
     * Firstly, it validates the headers. Then, it will go row by row to attempt to parse a {@code Person}.
     * If format of a field in row is incorrect, import will ignore and skip row.
     *
     * @param path the csv file path
     * @param model the model
     * @return a import result object
     * @throws CommandException if csv file is not in proper format
     */
    public CsvImportResult importCsv(Path path, Model model) throws CommandException {
        CsvImportResult result = new CsvImportResult();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String headerLine = reader.readLine();

            if (headerLine == null) {
                throw new CommandException("CSV file is empty.");
            }
            validateHeader(headerLine);

            String line;
            int rowNumber = 1; // header is row 1

            while ((line = reader.readLine()) != null) {
                rowNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }

                result.incrementProcessed();
                try {
                    Person person = parsePerson(line);

                    if (model.hasPerson(person)) {
                        result.addFailureMessage("Row " + rowNumber + ": duplicate person");
                        continue;
                    }
                    model.addPerson(person);
                    result.incrementSuccessful();

                } catch (IllegalArgumentException e) {
                    result.addFailureMessage("Row " + rowNumber + ": " + e.getMessage());
                }
            }
            return result;

        } catch (IOException e) {
            throw new CommandException("Could not read CSV file: " + e.getMessage());
        }
    }

    private void validateHeader(String headerLine) throws CommandException {
        List<String> actualHeaders = trimTrailingEmptyFields(parseCsvLine(headerLine))
                .stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        if (!actualHeaders.equals(EXPECTED_HEADERS)) {
            throw new CommandException(
                    "Invalid CSV header. Expected: " + String.join(",", EXPECTED_HEADERS));
        }
    }

    private Person parsePerson(String line) {
        List<String> values = trimTrailingEmptyFields(parseCsvLine(line));

        if (values.size() != EXPECTED_HEADERS.size()) {
            throw new IllegalArgumentException("wrong number of fields");
        }

        String nameString = values.get(0).trim();
        String roleString = values.get(1).trim();
        String addressString = values.get(2).trim();
        String phoneString = values.get(3).trim();
        String emailString = values.get(4).trim();
        String tagsString = values.get(5).trim();

        Name name = new Name(nameString);
        Role role = Role.valueOf(roleString.toUpperCase());
        Address address = new Address(addressString);
        Phone phone = new Phone(phoneString);
        Email email = new Email(emailString);
        Set<Tag> tags = parseTags(tagsString);

        return Person.createPerson(name, phone, email, address, tags, role);
    }

    private Set<Tag> parseTags(String tagsCell) {
        if (tagsCell.isEmpty()) {
            return Collections.emptySet();
        }

        return Arrays.stream(tagsCell.split(";"))
                .map(String::trim)
                .filter(tagName -> !tagName.isEmpty())
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Helper function to parse a single CSV row to list of field values.
     * <p>
     * Supports:
     * (1) comma-separated fields
     * (2) escaped commas to become literal (\,), escaped backslash too
     * (3) quotes are considered literal
     *
     * @throws IllegalArgumentException if the CSV row is malformed
     */
    private List<String> parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();

        boolean escaping = false;

        // just pray it works man
        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);

            if (escaping) {
                if (currentChar == ',' || currentChar == '\\') {
                    currentField.append(currentChar);
                } else {
                    currentField.append('\\').append(currentChar);
                }
                escaping = false;
            } else if (currentChar == '\\') {
                escaping = true;
            } else if (currentChar == ',') {
                fields.add(currentField.toString().trim());
                currentField.setLength(0);
            } else {
                currentField.append(currentChar);
            }
        }

        if (escaping) {
            currentField.append('\\');
        }

        fields.add(currentField.toString().trim());
        return fields;
    }

    private List<String> trimTrailingEmptyFields(List<String> fields) {
        // remove those padded commas (assumed to be unintended)
        int last = fields.size() - 1;
        while (last >= EXPECTED_HEADERS.size() && fields.get(last).isEmpty()) {
            last--;
        }
        return new ArrayList<>(fields.subList(0, last + 1));
    }
}
