package malbec.core.api

import malbec.core.biz.InstallationsService
import malbec.core.domain.FileInstallation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/installations")
open class InstallationController(
  val installationService: InstallationsService) {

    @GetMapping
    fun all() = installationService.selectAll()

    @GetMapping("/{id}")
    fun find(@PathVariable id: Int): FileInstallation {
        val r = installationService.find(id)
        return when {
            r.isRight() -> r.right().get()
            else -> throw r.left().get()
        }
    }   //END find

    @PutMapping("/add")
    fun add(@Validated @RequestBody item: FileInstallation): FileInstallation {
        val added = installationService.insert(item)
        return added
    }   //END add

    @PutMapping("/{id}")
    fun edit(@PathVariable id: Int, @RequestBody item: FileInstallation): FileInstallation {
        println("Editing item, id=$id, body=$item")

        val edited = installationService.update(item.copy(id = id))
        return when {
            edited.isRight() -> edited.right().get()
            else -> throw edited.left().get()
        }
    }   //END edit

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): Int {
        println("deleting $id")

        val deleted = installationService.delete(id)
        return when {
            deleted.isRight() -> deleted.right().get()
            else -> throw deleted.left().get()
        }
    }   //END delete

}   //END InstallationController
