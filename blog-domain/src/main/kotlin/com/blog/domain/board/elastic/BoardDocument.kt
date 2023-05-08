package com.blog.domain.board.elastic

import lombok.Getter
import lombok.NoArgsConstructor
import org.springframework.data.elasticsearch.annotations.*
import javax.persistence.Id

@Getter
@NoArgsConstructor
@Document(indexName = "board")
@Mapping(mappingPath = "elastic/board-mapping.json")
@Setting(settingPath = "elastic/board-setting.json")
class BoardDocument(
    @Id
    var id: Long = 0L,

    @Field(type = FieldType.Keyword)
    var title: String = "",

    @Field(type = FieldType.Text)
    var content: String = "",
) {
}