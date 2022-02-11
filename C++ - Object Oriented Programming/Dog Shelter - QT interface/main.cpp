#include "Ui.h"
#include "Repo_csv.h"
#include "Repo_html.h"
#include "Admin_gui.h"
#include <QApplication>

void run();

using namespace std;

string get_repo_type_user() {

    cout << "\n1. CSV" << endl;
    cout << "2. HTML" << endl;
    cout << "Choose the type of the file you want to see the adoption list (1 or 2): ";

    string user_repo_type;
    cin>>user_repo_type;
    return user_repo_type;

}

int main(int argc, char *argv[]) {
    QApplication a{argc, argv};

    Repo admin_repo;
    Repo user_repo;

//    if (get_repo_type_user() == "1") {
//        user_repo = new Repo_csv;
//        user_repo->set_file_name("adoption_list.csv");
//    } else {
//        user_repo = new Repo_html;
//        user_repo->set_file_name("adoption_list.html");
//    }

    Service s = Service(admin_repo, user_repo);
    s.read_data_file();

    auto admin = new Admin_gui(s);
    admin->show();

    //Ui console = Ui(s);
    //console.run_main_menu();

    return a.exec();

    return 0;
}


