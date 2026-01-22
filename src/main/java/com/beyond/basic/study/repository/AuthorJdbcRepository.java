package com.beyond.basic.study.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 수업 260117 오후 32:53

@Repository
public class AuthorJdbcRepository {
//  Datasource는 jdbc의 DB 관리 객체
    private final DataSource dataSource;
    @Autowired
    public AuthorJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//    jdbc의 단점
//    1. 쿼리 직접 작성 : 1)raw쿼리에서 오타가 나도 컴파일에러 X  2)데이터 추가시, 컬럼의 매핑을 수작업
//    2. 데이터 조립후 객체조립을 수작업 해야함
    public void save(Author author){
//    checked -> try/catch uncheck의 조상 RuntimeException으로.
        try {
            Connection connection = dataSource.getConnection();
            String sql = "insert into author(name, email, password) values(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, author.getName());
            ps.setString(2, author.getEmail());
            ps.setString(3, author.getPassword());
//            executeUpdate() : 추가/수정, executeQurey : 조회
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Author> findById(Long inputId){  //author가 있을수도 있고 없을수도 있어서 Optional로 설정
        Author author = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from author where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, inputId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){     //2줄이상이면 while문으로 반복
                Long id = rs.getLong("id"); //db의 컬럼명 "id"
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                author = Author.builder()
                        .id(id).name(name).email(email).password(password)
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(author);
    }

    public Optional<Author> findByEmail(String inputEmail){  //author가 있을수도 있고 없을수도 있어서 Optional로 설정
        Author author = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from author where email = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, inputEmail);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){     //2줄이상이면 while문으로 반복
                Long id = rs.getLong("id"); //db의 컬럼명 "id"
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                author = Author.builder()
                        .id(id).name(name).email(email).password(password)
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(author);
    }


    public List<Author> findAll(){
        List<Author> authorList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from author ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs =ps.executeQuery();
            while (rs.next()){     //2줄이상이면 while문으로 반복
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");     // dto에서 조립시 password를 빼니까 여기서는 안빼도 됨
                Author author = author = Author.builder()
                        .id(id).name(name).email(email).password(password)
                        .build();
                authorList.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authorList;
    }

    public void delete(Long id){
        try {
            Connection connection = dataSource.getConnection();
            String sql = "delete from author where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
//            executeUpdate() : 추가/수정/삭제, executeQurey : 조회
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
