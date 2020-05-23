import groovy.json.JsonSlurper

def run(data) {
  def jsonSluper = new JsonSlurper()
  def obj = jsonSluper.parseText(data)
  obj.total = 0
  Object lst = obj.bookList
  for (Object book in lst) {
    obj.total = (obj.total + book.price)
  }
  println(obj)
}

