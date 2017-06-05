package malbec.core.biz

import malbec.core.data.KeystoreDao
import malbec.core.domain.Project
import malbec.core.domain.SshKey
import org.funktionale.either.Either
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class KeystoreService(
  val keystoreDao: KeystoreDao) {

    @Transactional(readOnly = true)
    fun find(id: Int): Either<ObjectNotFoundException, SshKey> = keystoreDao.find(id)

    @Transactional(readOnly = true)
    fun selectAll(project: Project): List<SshKey> = keystoreDao.selectAll(project)

    @Transactional(readOnly = true)
    fun selectAll(projectId: Int): List<SshKey> = keystoreDao.selectAll(projectId)

    @Transactional
    fun insert(item: SshKey): SshKey = keystoreDao.insert(item)

    @Transactional
    fun update(item: SshKey): Either<ObjectNotFoundException, SshKey>
      = keystoreDao.update(item)

    @Transactional
    fun delete(id: Int): Either<ObjectNotFoundException, Int> = keystoreDao.delete(id)

}   //END KeystoreService