package malbec.core.biz

import malbec.core.data.ProjectsDao
import malbec.core.domain.Project
import org.funktionale.either.Either
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ProjectsService(
  private val projectsDao: ProjectsDao) {

    @Transactional(readOnly = true)
    open fun find(id: Int): Either<ObjectNotFoundException, Project> = projectsDao.find(id)

    @Transactional(readOnly = true)
    open fun selectAll(): List<Project> = projectsDao.selectAll()

    @Transactional
    open fun insert(item: Project): Project = projectsDao.insert(item)

    @Transactional
    open fun update(item: Project): Either<ObjectNotFoundException, Project>
      = projectsDao.update(item)

    @Transactional
    open fun delete(id: Int): Either<ObjectNotFoundException, Int> = projectsDao.delete(id)
}   //END ProjectsService