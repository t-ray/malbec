package malbec.core.api

import malbec.core.biz.ProjectsService
import malbec.core.domain.Project
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/projects")
open class ProjectsController(
  val projectsService: ProjectsService) {

    @GetMapping
    fun all() = projectsService.selectAll()

    @GetMapping("/{id}")
    fun find(@PathVariable id: Int): Project {
        val r = projectsService.find(id)
        return when {
            r.isRight() -> r.right().get()
            else -> throw r.left().get()
        }
    }   //END find

    @PutMapping("/add")
    fun add(@Validated @RequestBody item: Project): Project {
        val added = projectsService.insert(item)
        return added
    }   //END add

    @PutMapping("/{id}")
    fun edit(@PathVariable id: Int, @RequestBody item: Project): Project {
        println("Editing item, id=$id, body=$item")

        val edited = projectsService.update(item.copy(id = id))
        return when {
            edited.isRight() -> edited.right().get()
            else -> throw edited.left().get()
        }
    }   //END edit

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): Int {
        println("deleting $id")

        val deleted = projectsService.delete(id)
        return when {
            deleted.isRight() -> deleted.right().get()
            else -> throw deleted.left().get()
        }
    }   //END delete

}   //END ProjectsController