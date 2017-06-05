package malbec.core.api

import malbec.core.biz.RepositoriesService
import malbec.core.domain.Repository
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/projects")
open class RepositoryController(
  val repositoriesService: RepositoriesService) {

    @GetMapping("/{projectId}/repos")
    fun all(@PathVariable projectId: Int): List<Repository> = repositoriesService.selectAll(projectId)

    @GetMapping("/{projectId}/repos/{id}")
    fun find(@PathVariable id: Int): Repository {
        val r = repositoriesService.find(id)
        return when {
            r.isRight() -> r.right().get()
            else -> throw r.left().get()
        }
    }   //END find

    @PutMapping("/{projectId}/repos/add")
    fun add(@PathVariable projectId: Int, @Validated @RequestBody item: Repository): Repository {
        val added = repositoriesService.insert(item.copy(projectId = projectId))
        return added
    }   //END add

    @PutMapping("/{projectId}/repos/{id}")
    fun edit(@PathVariable id: Int, @RequestBody item: Repository): Repository {
        println("Editing item, id=$id, body=$item")

        val edited = repositoriesService.update(item.copy(id = id))
        return when {
            edited.isRight() -> edited.right().get()
            else -> throw edited.left().get()
        }
    }   //END edit

    @DeleteMapping("/{projectId}/repos/{id}")
    fun delete(@PathVariable id: Int): Int {
        println("deleting $id")

        val deleted = repositoriesService.delete(id)
        return when {
            deleted.isRight() -> deleted.right().get()
            else -> throw deleted.left().get()
        }
    }   //END delete

}   //END RepositoryController