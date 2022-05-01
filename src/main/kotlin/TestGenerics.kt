fun main(){
    val tranStudent = object: Transformer<Student>{
        override fun trasform(t: Student): String {
            return "${t.name} ${t.age} ${t.num}"
        }
    }
    val tranPerson = object: Transformer<Person>{
        override fun trasform(t: Person): String {
            return "${t.name} ${t.age}"
        }
    }
    handleTransformer(tranPerson)
}

fun handleTransformer(trans: Transformer<Student>){
    val student = Student("Tom", 19, 1234)
    val result = trans.trasform(student)
    println(result)
}

open class Person(val name: String, val age: Int)
class Student(name: String, age: Int, val num: Int):Person(name, age)

interface Transformer<in T>{
    fun trasform(t: T): String
}