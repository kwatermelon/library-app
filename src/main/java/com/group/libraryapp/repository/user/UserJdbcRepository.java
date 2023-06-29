package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //1.생성
    public void saveUser(String name, Integer age){
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)"; //mysql에 저장
        jdbcTemplate.update(sql, name, age); //데이터 변경 저장
    }

    //2. 조회
    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";  //유저테이블의 모든 정보를 가져오는 sql
        //ctrl+o : override
        //유저 정보를 UserResponse타입으로 바꿔주는 함수
        //RowMapper + alt+ Enter  = lamda 로 변형
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }

    //3. 수정

    // repository에서는 리퀘스트 모든 객체 쓰지 않아도 되므로 그냥 id만 받아옴.
    public boolean isUserNotExist(Long id){
        String readSql = "SELECT * FROM user WHERE id = ?";
        //조회됐을 때 결과가 있으면 무조건 0을 반환하고, ?에 값을 넣어주기 (id)
        //존재하지 않는 경우는 조회 결과가 비어있는 경우(존재하면 0이 나옴)
        //query : 반환된 값들을 List로 감싸줌
        //0은 최종적으로 List로 반환됨 (  [0]  )
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();

    }

    public void updateUserName(String name, long id) {
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        //특정 id를 가진 유저가 특정 이름으로 변경되어야함.
        jdbcTemplate.update(sql, name, id);
    }

    //4. 삭제
    public boolean isUserNotExist(String name){
        String readSql = "SELECT * FROM user WHERE name = ?"; //삭제는 이름을 기준으로 검색해야함
        //조회됐을 때 결과가 있으면 무조건 0을 반환하고, ?에 값을 넣어주기 (id)
        //존재하지 않는 경우는 조회 결과가 비어있는 경우(존재하면 0이 나옴)
        //query : 반환된 값들을 List로 감싸줌
        //0은 최종적으로 List로 반환됨 (  [0]  )
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();

    }

    public void deleteUser(String name){
        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);

    }
}