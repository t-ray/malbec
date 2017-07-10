package malbec.core.api

import malbec.core.biz.KeystoreService
import malbec.core.domain.SshKey
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/projects")
open class KeystoreController(
  val keystoreService: KeystoreService) {

    @GetMapping("/{projectId}/keys")
    fun all(@PathVariable projectId: Int) = keystoreService.selectAll(projectId)

    @GetMapping("/{projectId}/keys/{id}")
    fun find(@PathVariable id: Int): SshKey {
        val r = keystoreService.find(id)
        return when {
            r.isRight() -> r.right().get()
            else -> throw r.left().get()
        }
    }   //END find

    @PutMapping("/{projectId}/keys/add")
    fun add(@PathVariable projectId: Int, @Validated @RequestBody item: SshKey): SshKey {
        val added = keystoreService.insert(item.copy(projectId = projectId))
        return added
    }   //END add

    @PutMapping("/{projectId}/keys/{id}")
    fun edit(@PathVariable projectId: Int, @PathVariable id: Int, @RequestBody item: SshKey): SshKey {
        println("Editing item, id=$id, body=$item")

        val edited = keystoreService.update(item.copy(id = id, projectId = projectId))
        return when {
            edited.isRight() -> edited.right().get()
            else -> throw edited.left().get()
        }
    }   //END edit

    @DeleteMapping("/{projectId}/keys/{id}")
    fun delete(@PathVariable projectId: Int, @PathVariable id: Int): Int {
        println("deleting $id")

        val deleted = keystoreService.delete(id)
        return when {
            deleted.isRight() -> deleted.right().get()
            else -> throw deleted.left().get()
        }
    }   //END delete

}   //END KeystoreController