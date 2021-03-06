import groovy.json.JsonOutput

class DB {
  static void main(String[] args) {
    checkDatabase()
  }



  static void checkDatabase() {
    def sql = groovy.sql.Sql.newInstance('jdbc:mysql://localhost:3306/demo?useUnicode=true&serverTimezone=GMT%2B8&characterEncoding=UTF-8&useSSL=false', 'root', 'lzw719', 'com.mysql.cj.jdbc.Driver')
    //println sql.connection.catalog
    def rows = sql.rows("select * from t_group")
    println(rows)
    def json = JsonOutput.toJson(rows)
    println(json)
    sql.eachRow("select * from t_group") {
      tp ->
        println([tp.id, tp.name])
    }
    sql.close()
  }
}
