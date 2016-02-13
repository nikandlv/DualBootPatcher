include(ExternalProject)

include_directories(${OPENSSL_INCLUDE_DIR})

if(MBP_ENABLE_TESTS)
    include_directories(${GTEST_INCLUDE_DIRS})
endif()

set(MBSIGN_SOURCES
    src/mbsign.cpp
)

if(${MBP_BUILD_TARGET} STREQUAL desktop)
    add_definitions(-DMB_DYNAMIC_LINK)

    add_library(mbsign SHARED ${MBSIGN_SOURCES})

    target_compile_definitions(
        mbsign
        PRIVATE
        -DMB_LIBRARY
    )

    set_target_properties(
        mbsign
        PROPERTIES
        POSITION_INDEPENDENT_CODE 1
    )

    if(NOT MSVC)
        set_target_properties(
            mbsign
            PROPERTIES
            CXX_STANDARD 11
            CXX_STANDARD_REQUIRED 1
        )
    endif()

    target_link_libraries(
        mbsign
        mblog
        ${OPENSSL_CRYPTO_LIBRARY}
    )

    # Install library
    install(
        TARGETS mbsign
        LIBRARY DESTINATION ${LIB_INSTALL_DIR} COMPONENT Libraries
        RUNTIME DESTINATION ${LIB_INSTALL_DIR} COMPONENT Libraries
        #ARCHIVE DESTINATION ${LIB_INSTALL_DIR} COMPONENT Libraries
    )

    if(MBP_ENABLE_TESTS)
        add_executable(test_sign tests/test_sign.cpp)
        target_link_libraries(test_sign
            mbpio
            mbsign
            ${GTEST_BOTH_LIBRARIES}
        )

        if(NOT MSVC)
            set_target_properties(test_sign PROPERTIES
                CXX_STANDARD 11
                CXX_STANDARD_REQUIRED 1
                C_STANDARD 99
                C_STANDARD_REQUIRED 1
            )
        endif()

        add_test(test_sign test_sign)
    endif()
endif()

set(MBSIGN_SOURCES_STR "")
foreach(_source ${MBSIGN_SOURCES})
    set(MBSIGN_SOURCES_STR "${MBSIGN_SOURCES_STR} ${_source}")
endforeach()

# Generate Android.mk
configure_file(
    ${CMAKE_CURRENT_SOURCE_DIR}/Android.static.mk.in
    ${CMAKE_CURRENT_BINARY_DIR}/Android.static.mk
    @ONLY
)
configure_file(
    ${CMAKE_CURRENT_SOURCE_DIR}/Android.static.include.mk.in
    ${CMAKE_CURRENT_BINARY_DIR}/Android.static.include.mk
    @ONLY
)

ExternalProject_Add(
    mbsign-static-android
    SOURCE_DIR ${CMAKE_CURRENT_SOURCE_DIR}
    CONFIGURE_COMMAND ${CMAKE_COMMAND} -E remove_directory ${CMAKE_CURRENT_BINARY_DIR}/out/
    BUILD_COMMAND ${NDK_BUILD}
                  -C <SOURCE_DIR>
                  NDK_PROJECT_PATH=.
                  NDK_APPLICATION_MK=Application.mk
                  NDK_APP_LIBS_OUT=${CMAKE_CURRENT_BINARY_DIR}/out
                  NDK_OUT=<TMP_DIR>
                  APP_BUILD_SCRIPT=${CMAKE_CURRENT_BINARY_DIR}/Android.static.mk
                  ${NDK_ARGS}
    INSTALL_COMMAND ""
)

add_dependencies(
    mbsign-static-android
    mblog-static-android
)

# Always rebuild since files may have changed
ExternalProject_Add_Step(
    mbsign-static-android
    forcerebuild
    DEPENDEES update
    DEPENDERS configure
    ALWAYS 1
    COMMAND ""
)
