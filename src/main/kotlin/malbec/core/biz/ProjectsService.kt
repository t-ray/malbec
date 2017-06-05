package malbec.core.biz

import malbec.core.data.ProjectsDao
import malbec.core.domain.Project
import org.funktionale.either.Either
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ProjectsService(
  val projectsDao: ProjectsDao) {

    @Transactional(readOnly = true)
    fun find(id: Int): Either<ObjectNotFoundException, Project> = projectsDao.find(id)

    @Transactional(readOnly = true)
    fun selectAll(): List<Project> = projectsDao.selectAll()

    @Transactional
    fun insert(item: Project): Project = projectsDao.insert(item)

    @Transactional
    fun update(item: Project): Either<ObjectNotFoundException, Project>
      = projectsDao.update(item)

    @Transactional
    fun delete(id: Int): Either<ObjectNotFoundException, Int> = projectsDao.delete(id)
}   //END ProjectsService