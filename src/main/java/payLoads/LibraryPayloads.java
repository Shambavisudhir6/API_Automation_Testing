package payLoads;

public class LibraryPayloads {

    public static String addBook(String name, String isbn, String aisle, String author)
    {
        return String.format("""
            {
              "name": "%s",
              "isbn": "%s",
              "aisle": "%s",
              "author": "%s"
            }
            """, name, isbn, aisle, author);
    }

    public static String deleteBook(String id)
    {
        return String.format("""
                                {
                "ID" : "%s"
                }""", id);
    }
}
