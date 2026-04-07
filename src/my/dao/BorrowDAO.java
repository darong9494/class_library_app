package my.dao;

import com.tenco.library.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowDAO {
    // 도서 대출 처리
    // 대출 가능 여부 확인 >> borrow 테이블에 기록 >> book 테이블로 변경
    // try - with - resource 블록 문법 = 블록이 끝나는 순간 무조건 자원을 먼저 닫아버림
    // 트랜잭션 처리할때는 값을 확인해서 commit 또는 rollback해야 되기 때문에 사용하면 안됨
    // 직접 close() 처리를 해야함 - 트랜잭션 처리를 위해서.

    public void borrowBook(int bookId, int studentId) throws SQLException {
        Connection conn = null;

        conn = DatabaseUtil.getConnection();
        conn.setAutoCommit(false); // 트랜잭션 시작

        // 1. 대출 가능 여부 확인
        String checkSql = """
                select available from books where id = ?
                """;
        try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
            checkPstmt.setInt(1, bookId);

            try (ResultSet rs = checkPstmt.executeQuery()) {
                if (rs.next() == false) {
                    throw new SQLException("존재하지 않는 도서입니다: " + bookId);
                }
                if (rs.getBoolean("available") == false) {
                    throw new SQLException("현재 대출 중인 도서입니다. 반납후 이용가능");
                }
            }
        } // end of checkPstmt

        // 대출 가능한 한 상태 >> 대출 테이블에 학번, 책 번호를 기록해야함.
        // 2. 대출 기록 축가
        String borrowSql = """
                insert into borrows(book_id,student_id,borrow_date) values (?, ?, ?)
                """;








    }
}
