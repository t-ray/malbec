package malbec.core.biz

import malbec.core.data.InstallationsDao
import malbec.core.domain.FileInstallation
import org.funktionale.either.Either
import org.springframework.stereotype.Service

@Service
open class InstallationsService(
  val installationsDao: InstallationsDao) {

    fun find(id: Int): Either<ObjectNotFoundException, FileInstallation> = installationsDao.find(id)

    fun selectAll(): List<FileInstallation> = installationsDao.selectAll()

    fun insert(installation: FileInstallation): FileInstallation = installationsDao.insert(installation)

    fun update(installation: FileInstallation): Either<ObjectNotFoundException, FileInstallation>
      = installationsDao.update(installation)

    fun delete(id: Int): Either<ObjectNotFoundException, Int> = installationsDao.delete(id)
}   //END InstallationsService