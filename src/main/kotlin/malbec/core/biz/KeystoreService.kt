package malbec.core.biz

import malbec.core.data.KeystoreDao
import malbec.core.domain.Project
import malbec.core.domain.SshKey
import org.funktionale.either.Either
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class KeystoreService(
  private val keystoreDao: KeystoreDao) {

    @Transactional(readOnly = true)
    open fun find(id: Int): Either<ObjectNotFoundException, SshKey> = keystoreDao.find(id)

    @Transactional(readOnly = true)
    open fun selectAll(project: Project): List<SshKey> = keystoreDao.selectAll(project)

    @Transactional(readOnly = true)
    open fun selectAll(projectId: Int): List<SshKey> = keystoreDao.selectAll(projectId)

    @Transactional
    open fun insert(item: SshKey): SshKey = keystoreDao.insert(item)

    @Transactional
    open fun update(item: SshKey): Either<ObjectNotFoundException, SshKey>
      = keystoreDao.update(item)

    @Transactional
    open fun delete(id: Int): Either<ObjectNotFoundException, Int> = keystoreDao.delete(id)

}   //END KeystoreService