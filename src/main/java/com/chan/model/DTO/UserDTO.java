package com.chan.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Chan
 * @Date: 2020/6/10 17:23
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    private String name;

    private List<Long> roles;

}
