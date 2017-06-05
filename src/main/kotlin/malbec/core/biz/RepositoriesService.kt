package malbec.core.biz

import malbec.core.data.RepositoriesDao
import malbec.core.domain.Project
import malbec.core.domain.Repository
import org.funktionale.either.Either
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class RepositoriesService(
  private val repositoriesDao: RepositoriesDao) {

    @Transactional(readOnly = true)
    open fun find(id: Int): Either<ObjectNotFoundException, Repository> = repositoriesDao.find(id)

    @Transactional(readOnly = true)
    open fun selectAll(project: Project): List<Repository> = repositoriesDao.selectAll(project)

    @Transactional(readOnly = true)
    open fun selectAll(projectId: Int): List<Repository> = repositoriesDao.selectAll(projectId)

    @Transactional
    open fun insert(item: Repository): Repository = repositoriesDao.insert(item)

    @Transactional
    open fun update(item: Repository): Either<ObjectNotFoundException, Repository>
      = repositoriesDao.update(item)

    @Transactional
    open fun delete(id: Int): Either<ObjectNotFoundException, Int> = repositoriesDao.delete(id)

}   //END RepositoriesService