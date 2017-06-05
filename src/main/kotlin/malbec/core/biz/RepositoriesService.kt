package malbec.core.biz

import malbec.core.data.RepositoriesDao
import malbec.core.domain.Project
import malbec.core.domain.Repository
import org.funktionale.either.Either
import org.springframework.stereotype.Service

@Service
open class RepositoriesService(
  val repositoriesDao: RepositoriesDao) {

    fun find(id: Int): Either<ObjectNotFoundException, Repository> = repositoriesDao.find(id)

    fun selectAll(project: Project): List<Repository> = repositoriesDao.selectAll(project)

    fun selectAll(projectId: Int): List<Repository> = repositoriesDao.selectAll(projectId)

    fun insert(item: Repository): Repository = repositoriesDao.insert(item)

    fun update(item: Repository): Either<ObjectNotFoundException, Repository>
      = repositoriesDao.update(item)

    fun delete(id: Int): Either<ObjectNotFoundException, Int> = repositoriesDao.delete(id)

}   //END RepositoriesService