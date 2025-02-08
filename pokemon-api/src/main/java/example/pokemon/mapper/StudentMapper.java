package example.pokemon.mapper;

import example.pokemon.dto.StudentDto;
import example.pokemon.model.Student;
import example.pokemon.model.StudentDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class StudentMapper {
    @Mapping(target = "id", ignore = true)
    public abstract Student mapDtoToStudent(StudentDto studentDto);
    public abstract StudentDto mapToDto(Student student);
    public abstract StudentDto mapToDto(StudentDocument studentDocument);
}

