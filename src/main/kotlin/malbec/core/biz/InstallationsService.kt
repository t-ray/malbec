package malbec.core.biz

import malbec.core.data.InstallationsDao
import malbec.core.domain.FileInstallation
import org.funktionale.either.Either
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class InstallationsService(
  val installationsDao: InstallationsDao) {

    @Transactional(readOnly = true)
    fun find(id: Int): Either<ObjectNotFoundException, FileInstallation> = installationsDao.find(id)

    @Transactional(readOnly = true)
    fun selectAll(): List<FileInstallation> = installationsDao.selectAll()

    @Transactional
    fun insert(installation: FileInstallation): FileInstallation = installationsDao.insert(installation)

    @Transactional
    fun update(installation: FileInstallation): Either<ObjectNotFoundException, FileInstallation>
      = installationsDao.update(installation)

    @Transactional
    fun delete(id: Int): Either<ObjectNotFoundException, Int> = installationsDao.delete(id)
}   //END InstallationsService