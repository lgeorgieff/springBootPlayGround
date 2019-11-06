package com.apress.todo.model

import org.springframework.stereotype.Repository

@Repository
class ToDoRepository : CommonRepository<ToDo> {
    private val mToDos = mutableMapOf<String, ToDo>()

    override fun save(model: ToDo): ToDo {
        if (mToDos.containsKey(model.id)) mToDos.remove(model.id)
        mToDos[model.id] = model
        return model
    }

    override fun save(models: List<ToDo>) = models.map { save(it) }

    override fun findById(id: String) =
            tryFindById(id) ?: throw NullPointerException("""ToDo id "$id" not found""")

    override fun tryFindById(id: String) = mToDos[id]

    override fun findAll() = mToDos.values.toList()

    override fun delete(model: ToDo) {
        mToDos.remove(model.id)
    }
}
