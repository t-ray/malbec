package malbec.core.biz

import malbec.core.data.ProjectsDao
import malbec.core.domain.Project
import org.funktionale.either.Either
import org.springframework.stereotype.Service

@Service
open class ProjectsService(
  val projectsDao: ProjectsDao) {

    fun find(id: Int): Either<ObjectNotFoundException, Project> = projectsDao.find(id)

    fun selectAll(): List<Project> = projectsDao.selectAll()

    fun insert(item: Project): Project = projectsDao.insert(item)

    fun update(item: Project): Either<ObjectNotFoundException, Project>
      = projectsDao.update(item)

    fun delete(id: Int): Either<ObjectNotFoundException, Int> = projectsDao.delete(id)
}   //END ProjectsService