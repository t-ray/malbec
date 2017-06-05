package malbec.core.biz

import malbec.core.data.KeystoreDao
import malbec.core.domain.Project
import malbec.core.domain.SshKey
import org.funktionale.either.Either
import org.springframework.stereotype.Service

@Service
open class KeystoreService(
  val keystoreDao: KeystoreDao) {

    fun find(id: Int): Either<ObjectNotFoundException, SshKey> = keystoreDao.find(id)

    fun selectAll(project: Project): List<SshKey> = keystoreDao.selectAll(project)

    fun selectAll(projectId: Int): List<SshKey> = keystoreDao.selectAll(projectId)

    fun insert(item: SshKey): SshKey = keystoreDao.insert(item)

    fun update(item: SshKey): Either<ObjectNotFoundException, SshKey>
      = keystoreDao.update(item)

    fun delete(id: Int): Either<ObjectNotFoundException, Int> = keystoreDao.delete(id)

}   //END KeystoreService