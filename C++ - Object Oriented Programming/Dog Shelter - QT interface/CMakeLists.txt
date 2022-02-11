cmake_minimum_required(VERSION 3.17)
project(a89_917TudorTise)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

set(CMAKE_PREFIX_PATH "/Users/tudor/Qt/5.15.2/clang_64/lib/cmake")
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(QT_VERSION 5)
set(REQUIRED_LIBS Core Gui Widgets)
set(REQUIRED_LIBS_QUALIFIED Qt5::Core Qt5::Gui Qt5::Widgets)

add_executable(a89_917TudorTise
        Dog.cpp
        Dog.h
        InputException.cpp
        InputException.h
        main.cpp
        Repo.cpp
        Repo.h
        Repo_csv.cpp
        Repo_csv.h
        Repo_html.cpp
        Repo_html.h
        Service.cpp
        Service.h
        Ui.cpp
        Ui.h
        Validator.cpp
        Validator.h
        Admin_gui.cpp
        Admin_gui.h
        User_gui.cpp
        User_gui.h)


find_package(Qt${QT_VERSION} COMPONENTS ${REQUIRED_LIBS} REQUIRED)
target_link_libraries(${PROJECT_NAME} ${REQUIRED_LIBS_QUALIFIED})