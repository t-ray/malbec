package malbec.core.biz

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ObjectNotFoundException(message: String = ""): RuntimeException(message)