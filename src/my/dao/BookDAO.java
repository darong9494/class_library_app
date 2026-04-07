package my.dao;

import com.tenco.library.util.DatabaseUtil;
import my.dto.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // 도서추가
    public int addBook(Book book) throws SQLException {
        String sql = """
                INSERT INTO books(title, author, publisher, publication_year, isbn)
                                values(?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseUtil.getConnection()) {
            // Connection = 데이터베이스와의 연결상태를 담는 객체
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, book.getTitle()); // 첫번째 ? 자리에 값 할당
            pstmt.setString(2, book.getAuthor()); // 두번째 ? 자리...
            pstmt.setString(3, book.getPublisher());
            pstmt.setInt(4, book.getPublicationYear());
            pstmt.setString(5, book.getIsbn());

            int rows = pstmt.executeUpdate(); // pstmt로 데이터 변경을 실행하고 그결과의 행row의 개수를 받아오는 문장
            // int rows = 쿼리 실행 결과로 변경,삭제,삽입된 행의 총 개수가 정수 형태로 저장된다.
            return rows;
        }
    }

    // 도서 전체 조회
    public List<Book> getAllBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String sql = """
                SELECT * FROM books ORDER By id
                """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                bookList.add(mapToBook(rs));
            }
        }
        return bookList;
    }

    private Book mapToBook(ResultSet rs) throws SQLException {


        return Book.builder()
                // 위에서 여러번 써야하는거를 rs라고 선언한걸로 정리해서 써먹겠다.
                // Book book = new Book; 과 비슷한...
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .author(rs.getString("author"))
                .publisher(rs.getString("publisher"))
                .publicationYear(rs.getInt("publication_year"))
                .isbn(rs.getString("isbn"))
                .available(rs.getBoolean("available"))
                .build();
    }

    // 제목으로 도서 검색
    public List<Book> searchBooksByTitle(String title) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String sql = """
                select * from books where title like ?
                """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookList.add(mapToBook(rs));

                }
            }
        }
        return bookList;
    }

    // 테스트 코드 작성
    public static void main(String[] args) {
        List<Book> resultList = null;
        try {
            resultList = new BookDAO().searchBooksByTitle("입문");
            System.out.println(resultList);
            System.out.println("-------------");
            System.out.println(new BookDAO().getAllBooks());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
