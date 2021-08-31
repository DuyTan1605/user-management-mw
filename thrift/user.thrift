namespace java com.vng.zing.userservice.thrift

enum Gender {
    Male,
    Female
}

struct User {
    1: i32 id,
    2: string name,
    3: string username,
    4: string password,
    5: Gender gender,
    6: i64 birthday,
    7: i64 createtime,
    8: i64 updatetime
}

struct ListUserParams{

}

struct ListUserResult{
    1: i32 code,
    2: list<User> data
}

struct DetailUserParams {
    1: i32 id
}   

struct DetailUserResult{
    1: i32 code,
    2: User data
}

struct CreateUserParams{
    1: User user
}

struct CreateUserResult{
    1: i32 code,
    2: string message
}

struct UpdateUserParams{
    1: User user
}

struct UpdateUserResult{
    1: i32 code,
    2: string message
}

struct DeleteUserParams {
    1: i32 id
}

struct DeleteUserResult{
    1: i32 code,
    2: string message
}

service UserService {
    ListUserResult getUsers(1: ListUserParams params),
    DetailUserResult getUser(1: DetailUserParams params),
    CreateUserResult createUser(1: CreateUserParams params),
    UpdateUserResult updateUser(1: UpdateUserParams params),
    DeleteUserResult deleteUser(1: DeleteUserParams params)
}

