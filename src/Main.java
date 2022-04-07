import classes.GameProgress;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

	private static final String PATH = "/Users/aleksey/projects/files/savegames";

	public static void main(String[] args) {
		GameProgress gameProgress1 = new GameProgress(100, 2, 1, 2.2);
		GameProgress gameProgress2 = new GameProgress(98, 4, 2, 21.2);
		GameProgress gameProgress3 = new GameProgress(50, 7, 9, 192.2);
		GameProgress gameProgress4 = new GameProgress(10, 3, 1, 3.7);

		saveGame(PATH + "/save1.dat", gameProgress1);
		saveGame(PATH + "/save2.dat", gameProgress2);
		saveGame(PATH + "/save3.dat", gameProgress3);
		saveGame(PATH + "/save4.dat", gameProgress4);

		archiveAll();

	}

	public static void saveGame(String filename, GameProgress progress) {

		try (FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(progress);
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}

	public static void archiveAll() {

		try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(PATH + "/saved_games.zip"))) {

			File dir = new File(PATH);

			for (File file : dir.listFiles()) {
					if (file.getName().contains("dat")) {
						FileInputStream fis = new FileInputStream(file.getAbsolutePath());
						ZipEntry zipEntry = new ZipEntry(file.getName());
						zip.putNextEntry(zipEntry);
						byte[] buffer = new byte[fis.available()];
						fis.read(buffer);
						zip.write(buffer);
						zip.closeEntry();
						file.delete();
					}
				}


		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}

	}
}

