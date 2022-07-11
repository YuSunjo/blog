package com.blog.adminService.upload

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.blog.dto.upload.ImageType
import com.blog.service.upload.S3Component
import com.blog.service.upload.S3Service
import com.blog.service.upload.UploadServiceImpl
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItem
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.TestConstructor
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.testcontainers.shaded.org.apache.commons.io.IOUtils
import java.io.*
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*


@ExtendWith(MockKExtension::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UploadServiceTest {

    @InjectMockKs
    private lateinit var uploadServiceImpl: UploadServiceImpl

    @InjectMockKs
    private lateinit var s3Service: S3Service

    @MockK(relaxed = true)
    private lateinit var amazonS3Client: AmazonS3Client

    @MockK(relaxed = true)
    private lateinit var s3Component: S3Component

    @Test
    fun uploadServiceTest() {
        // given
        val multipartFile = getMultipartFile()

        // stub 만들어주기
        val uploadImageName = "testImage"
        val type = ".jpeg"
        val now = SimpleDateFormat("yyyyMMddHmsS").format(Date())
        val folder = ImageType.USER
        val fileName = "$now/$folder/$uploadImageName"
        val returnFile = "https://d10mjr19pnf1jr.cloudfront.net/$folder/$now/$uploadImageName$type"

        val inputStream = multipartFile.inputStream
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = multipartFile.size
        objectMetadata.contentType = multipartFile.contentType
        println(returnFile)
        every {
            s3Service.uploadFile(inputStream, objectMetadata, fileName)
        } returns returnFile

        // when
        val imageUpload = uploadServiceImpl.imageUpload(multipartFile, ImageType.USER)

        // then
        Assertions.assertThat(imageUpload).isEqualTo("")
    }

    @Throws(IOException::class)
    private fun getMultipartFile(): MultipartFile {
        val file = File(File("").absolutePath + "/src/main/resources/static/images/testImage.jpeg")
        val fileItem: FileItem = DiskFileItem(
            "originFile",
            Files.probeContentType(file.toPath()),
            false,
            file.name,
            file.length().toInt(),
            file.parentFile
        )
        try {
            val input: InputStream = FileInputStream(file)
            val os: OutputStream = fileItem.getOutputStream()
            IOUtils.copy(input, os)
            // Or faster..
            // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
        } catch (ex: IOException) {
            // do something.
        }

        //jpa.png -> multipart 변환
        return CommonsMultipartFile(fileItem)
    }

}